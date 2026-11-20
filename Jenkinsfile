
pipeline {
    agent any

    environment {
        JAVA_HOME = 'C:\\Program Files\\Eclipse Adoptium\\jdk-21.0.4.7-hotspot'
        MAVEN_HOME = 'D:\\DevOps\\Tools\\apache-maven-3.10.0-bin\\apache-maven-3.10.0'

        PATH = "C:\\Windows\\System32;${JAVA_HOME}\\bin;${MAVEN_HOME}\\bin;C:\\Users\\kvsvy\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin;${env.PATH}"

        DOCKER_IMAGE = 'student-management-web:latest'
        CONTAINER_NAME = 'student-management-web-container'
    }

    stages {

        stage('Build') {
            steps {
                echo 'Building Student Management System...'
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Running JUnit Tests...'
                bat 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging Spring Boot Application...'
                bat 'mvn package'
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building Docker Image...'
                bat 'docker build -t %DOCKER_IMAGE% .'
            }
        }

        stage('Docker Deploy') {
            steps {
                echo 'Deploying Student Management Application...'

                bat '''
                    docker rm -f %CONTAINER_NAME% 2>NUL
                    docker run -d --name %CONTAINER_NAME% -p 8081:8081 -v student-data:/app/data %DOCKER_IMAGE%
                '''
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'target/*.jar',
                             fingerprint: true

            echo 'Build, Test, Docker Build and Deployment completed successfully!'
        }

        failure {
            echo 'Pipeline failed. Check the console output.'
        }
    }
}

