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

        N8N_WEBHOOK =
            'http://localhost:5678/webhook/jenkins-qa'
    }
    stages {

        stage('Checkout Application') {
            steps {
                dir('student-management-system') {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: "*/${SYSTEM_BRANCH}"]],
                        userRemoteConfigs: [[
                            url: "${SYSTEM_REPOSITORY}"
                        ]]
                    ])
                }
            }
        }

        stage('Check Deployment') {
            steps {
                echo 'Checking existing Student Management System'

                powershell '''
                    $ErrorActionPreference = "Stop"

                    function Wait-ForUrl {
                        param(
                            [string]$Url,
                            [string]$Name,
                            [int]$Attempts = 12,
                            [int]$DelaySeconds = 5
                        )

                        for ($i = 1; $i -le $Attempts; $i++) {
                            try {
                                $response = Invoke-WebRequest `
                                    -Uri $Url `
                                    -UseBasicParsing `
                                    -TimeoutSec 10

                                if ($response.StatusCode -ge 200 -and
                                    $response.StatusCode -lt 400) {
                                    Write-Host "$Name is available"
                                    return
                                }
                            }
                            catch {
                                Write-Host "Waiting for $Name ($i/$Attempts)"
                            }

                            Start-Sleep -Seconds $DelaySeconds
                        }

                        throw "$Name unavailable: $Url"
                    }

                    Wait-ForUrl `
                        -Url $env:API_URL `
                        -Name "Spring Boot API"

                    Wait-ForUrl `
                        -Url $env:FRONTEND_URL `
                        -Name "Angular Frontend"
                '''

                bat '''
                    docker ps --filter "name=student-"
                '''
            }
        }

        stage('Checkout Tests') {
            steps {
                dir('student-management-Test') {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: "*/${TEST_BRANCH}"]],
                        userRemoteConfigs: [[
                            url: "${TEST_REPOSITORY}"
                        ]]
                    ])
                }
            }
        }

        stage('System Testing') {
            steps {
                dir('student-management-Test') {
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
                // 1. Publish JUnit results
                junit(
                    allowEmptyResults: true,
                    testResults:
                        'student-management-Test/target/surefire-reports/*.xml'
                )

                // 2. Archive Cucumber and test reports
                archiveArtifacts(
                    allowEmptyArchive: true,
                    artifacts: '''
                        student-management-Test/target/cucumber-report.html,
                        student-management-Test/target/cucumber-report.json,
                        student-management-Test/target/surefire-reports/**/*
                    '''
                )

                // 3. Get final Jenkins status
                def qaStatus = currentBuild.currentResult

                // 4. Notify n8n
                try {
                    withEnv([
                        "QA_JOB=${env.JOB_NAME}",
                        "QA_BUILD=${env.BUILD_NUMBER}",
                        "QA_STATUS=${qaStatus}",
                        "QA_URL=${env.BUILD_URL ?: ''}"
                    ]) {
                        powershell '''
                            $ErrorActionPreference = "Stop"

                            $payload = @{
                                job = $env:QA_JOB
                                build = [int]$env:QA_BUILD
                                status = $env:QA_STATUS
                                source = "Jenkins"
                                url = $env:QA_URL
                            } | ConvertTo-Json

                            Invoke-RestMethod `
                                -Uri $env:N8N_WEBHOOK `
                                -Method POST `
                                -ContentType "application/json" `
                                -Body $payload `
                                -TimeoutSec 10

                            Write-Host "Notification envoyee a n8n"
                        '''
                    }
                }
                catch (Exception e) {
                    echo "n8n notification failed: ${e.message}"
                }
            }
        }

        success {
            echo 'QA pipeline completed successfully.'
        }

        unstable {
            echo 'QA pipeline unstable: review test failures.'
        }

        failure {
            echo 'QA pipeline failed: review Jenkins logs.'
        }
    }
}
