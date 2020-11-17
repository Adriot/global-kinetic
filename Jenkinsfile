pipeline {
    agent {label "master"}
    tools {
        jdk 'jdk1.8.0_211'
    }
    stages {
        stage('Driver Manager Factory Tests') {
            steps {
                sh './gradlew test --tests selenium.web.driver.DriverManagerFactoryTests --stacktrace'
            }
        }
    }
}