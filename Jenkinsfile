pipeline {
  agent any

  options {
    timestamps()
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Test') {
        steps {
          sh '''
            docker run --rm \
              -v "$PWD":/workspace \
              -w /workspace \
              maven:3.9.6-eclipse-temurin-17 \
              mvn -B test
          '''
        }
        post {
          always {
            junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
          }
        }
      }

   stage('Package') {
       steps {
         sh '''
           docker run --rm \
             -v "$PWD":/workspace \
             -w /workspace \
             maven:3.9.6-eclipse-temurin-17 \
             mvn -B -DskipTests package
         '''
         archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
       }
     }

    stage('Build Docker Image') {
      steps {
        sh 'docker build -t springapi:ci .'
      }
    }

    stage('Smoke Test (Actuator)') {
      steps {
        sh '''
          docker rm -f springapi-ci || true
          docker run -d --name springapi-ci -p 18080:8080 springapi:ci
          for i in $(seq 1 30); do
            curl -fsS http://localhost:18080/actuator/health && exit 0
            sleep 2
          done
          echo "Health check failed"
          docker logs springapi-ci || true
          exit 1
        '''
      }
      post {
        always { sh 'docker rm -f springapi-ci || true' }
      }
    }
  }
}