pipeline {
    agent any 
    environment {
        DOCKER_IMAGE_BACK = "85devs/config-server"
        DOCKER_TAG = "v${BUILD_NUMBER}"
        CONTAINER_NAME_BACK = "config-server"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run Docker Compose') {
            steps {
                script {
                    sh "pwd"
                    sh "ls -la"
                    echo "Parando e removendo qualquer contêiner existente com os nomes: ${CONTAINER_NAME_BACK}"
                    sh "docker-compose down || true"
                    echo "Buildando o Docker Compose"
                    sh "docker-compose build"
                    echo "Executando o Docker Compose"
                    sh "docker-compose up -d"
                }
            }
        }
    }

    post {
        always {
            script {
                echo "Limpando imagens Docker antigas não usadas"
                sh "docker image prune -f"
            }
        }
        success {
            echo "Pipeline concluído com sucesso!"
        }
        failure {
            echo "Pipeline falhou."
        }
    }
}