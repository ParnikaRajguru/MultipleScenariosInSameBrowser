pipeline {
    agent {
        label 'maven'
    }
    stages {
        stage('Build') {
            steps {
                // Checkout code
                git 'https://github.com/ParnikaRajguru/MultipleScenariosInSameBrowser.git'
                // Run Maven build
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                // Run Maven tests
                sh 'mvn test'
            }
        }
    }
}