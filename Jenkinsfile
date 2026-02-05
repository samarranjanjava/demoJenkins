pipeline {
    agent any

    environment {
        IMAGE_NAME = "2016a/springboot-app"
        IMAGE_TAG  = "${env.BUILD_NUMBER}"
    }

    stages {

        stage('Build with Maven') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Run Unit Tests') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }

       stage('Docker Image Security Scan (DevSecOps)') {
		    steps {
		        echo "⚠️ Trivy not installed yet – skipping security scan"
		    }
		}

        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    bat """
                    echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                    """
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                bat "docker push ${IMAGE_NAME}:${IMAGE_TAG}"
            }
        }

        stage('Deploy Application') {
            steps {
                bat """
                docker stop springboot || echo Container not running
                docker rm springboot || exit 0
                docker run -d -p 8080:8080 --name springboot ${IMAGE_NAME}:${IMAGE_TAG}
                """
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline completed successfully"
        }
        failure {
            echo "❌ Pipeline failed"
        }
    }
}
