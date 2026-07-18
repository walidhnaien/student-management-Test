pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
        timestamps()
        disableConcurrentBuilds()
    }

    tools {
        jdk 'jdk-17'
        maven 'M3'
    }

    environment {
        SYSTEM_REPOSITORY =
            'https://github.com/walidhnaien/student-management-system.git'

        TEST_REPOSITORY =
            'https://github.com/walidhnaien/student-management-Test.git'

        SYSTEM_BRANCH = 'master'
        TEST_BRANCH = 'master'

        FRONTEND_URL = 'http://localhost:4200'
        API_URL = 'http://localhost:8080/api/students'
    }
    stages {

        stage('Deployment Test') {
            steps {
                echo 'Checkout and deploy Student Management System'

                dir('student-management-system') {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[
                            name: "*/${SYSTEM_BRANCH}"
                        ]],
                        userRemoteConfigs: [[
                            url: "${SYSTEM_REPOSITORY}"
                        ]]
                    ])

                    bat '''
                        docker version
                        docker compose version
                        docker rm -f student-postgres 2>$null
                        docker compose down -v --remove-orphans
                        docker compose up -d --build

                        docker compose ps
                    '''

                    powershell '''
                        $ErrorActionPreference = "Stop"

                        function Wait-ForUrl {
                            param(
                                [string]$Url,
                                [string]$Name,
                                [int]$Attempts = 40,
                                [int]$DelaySeconds = 5
                            )

                            Write-Host "Waiting for $Name: $Url"

                            for ($attempt = 1; $attempt -le $Attempts; $attempt++) {
                                try {
                                    $response = Invoke-WebRequest `
                                        -Uri $Url `
                                        -UseBasicParsing `
                                        -TimeoutSec 10

                                    if ($response.StatusCode -ge 200 `
                                        -and $response.StatusCode -lt 400) {

                                        Write-Host "$Name is available."
                                        Write-Host "HTTP status: $($response.StatusCode)"
                                        return
                                    }
                                }
                                catch {
                                    Write-Host `
                                        "Attempt $attempt/$Attempts failed for $Name."
                                }

                                Start-Sleep -Seconds $DelaySeconds
                            }

                            throw "$Name did not become available: $Url"
                        }

                        Wait-ForUrl `
                            -Url $env:API_URL `
                            -Name "Spring Boot API"

                        Wait-ForUrl `
                            -Url $env:FRONTEND_URL `
                            -Name "Angular frontend"
                    '''

                    bat '''
                        docker compose ps

                        docker inspect ^
                          --format="{{.State.Status}}" ^
                          student-postgres

                        docker inspect ^
                          --format="{{.State.Status}}" ^
                          student-backend

                        docker inspect ^
                          --format="{{.State.Status}}" ^
                          student-frontend
                    '''
                }
            }
        }

        stage('System Testing') {
            steps {
                echo 'Checkout and execute Cucumber/Selenium tests'

                dir('student-management-Test') {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[
                            name: "*/${TEST_BRANCH}"
                        ]],
                        userRemoteConfigs: [[
                            url: "${TEST_REPOSITORY}"
                        ]]
                    ])

                    bat '''
                        java -version
                        mvn -version

                        mvn -B clean test ^
                          -Dheadless=true ^
                          -DbaseUrl=%FRONTEND_URL%
                    '''
                }
            }
        }
    }

    post {
        always {
            script {
                if (fileExists(
                    'student-management-system/docker-compose.yml'
                )) {
                    dir('student-management-system') {
                        bat '''
                            docker compose ps > docker-compose-status.log 2>&1
                            docker compose logs --no-color > docker-compose.log 2>&1
                        '''
                    }
                }
            }

            junit(
                allowEmptyResults: true,
                testResults:
                    'student-management-Test/target/surefire-reports/*.xml'
            )

            archiveArtifacts(
                allowEmptyArchive: true,
                artifacts: '''
                    student-management-Test/target/cucumber-report.html,
                    student-management-Test/target/cucumber-report.json,
                    student-management-Test/target/surefire-reports/**/*,
                    student-management-system/docker-compose.log,
                    student-management-system/docker-compose-status.log
                '''
            )
        }

        success {
            echo 'Deployment and system tests completed successfully.'
        }

        failure {
            echo '''
                Pipeline failed.
                Review the Docker logs and Cucumber test report.
            '''
        }

        cleanup {
            script {
                if (fileExists(
                    'student-management-system/docker-compose.yml'
                )) {
                    dir('student-management-system') {
                        bat '''
                            docker compose down -v --remove-orphans
                        '''
                    }
                }
            }
        }
    }
}