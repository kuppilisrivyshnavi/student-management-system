
pipeline {

    agent any

    environment {
        JAVA_HOME = 'C:\\Program Files\\Eclipse Adoptium\\jdk-21.0.4.7-hotspot'
        MAVEN_HOME = 'D:\\DevOps\\Tools\\apache-maven-3.10.0-bin\\apache-maven-3.10.0'
        PATH = "${JAVA_HOME}\\bin;${MAVEN_HOME}\\bin;${env.PATH}"
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
                echo 'Packaging Java Application...'
                bat 'mvn package'
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'target/*.jar',
                             fingerprint: true
            echo 'Build completed successfully!'
        }

        failure {
            echo 'Build failed. Check the console output.'
        }
    }
}
