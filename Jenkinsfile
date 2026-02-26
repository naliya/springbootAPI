pipeline {
  agent any

  options {
    timestamps()
  }

  stages {
      stage('Debug Workspace') {
        steps {
          sh '''
            pwd
            ls -la
            find . -maxdepth 3 -name pom.xml -print
          '''
        }
      }

      stage('Test') {
        steps {
          sh 'chmod +x mvnw'
          sh './mvnw -B test'
        }
        post {
          always {
            junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
          }
        }
      }

      stage('Package') {
        steps {
          sh 'chmod +x mvnw'
          sh './mvnw -B -DskipTests package'
          archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
      }

//       stage('Test') {
//         steps {
//           echo 'No tests yet — skipping'
//         }
//       }
//
//    stage('Package') {
//        steps {
//          sh '''
//            docker run --rm \
//              -v "$PWD":/workspace \
//              -w /workspace \
//              maven:3.9.6-eclipse-temurin-17 \
//              mvn -B -DskipTests package
//          '''
//          archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
//        }
//      }

    stage('Build Docker Image') {
      steps {
        sh 'docker build -t springapi:ci .'
      }
    }

    stage('Smoke Test (Actuator)') {
     steps {
         sh '''
           docker rm -f springapi-ci || true

           # Run app container on the same Docker network as Jenkins (default compose network)
           docker run -d --name springapi-ci --network springapi_default springapi:ci

           # Wait and curl the container by name on port 8080
           for i in $(seq 1 30); do
             if docker exec jenkins curl -fsS http://springapi-ci:8080/actuator/health > /dev/null; then
               echo "Smoke test passed"
               exit 0
             fi
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