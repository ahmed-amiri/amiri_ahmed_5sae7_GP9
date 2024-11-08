pipeline {
    agent any
    tools {
        maven 'M2_HOME'
    }

    stages {
        stage('GIT'){
            steps{
            git branch: 'master',
            url : 'https://github.com/ahmed-amiri/amiri_ahmed_5sae7_GP9.git';

            }
            }

         stage('Maven Clean') {
            steps {
                echo 'Nettoyage du Projet : ';
                sh 'mvn clean';
            }
        }

         stage('Maven Compile') {
            steps {
                echo 'Construction du Projet : ';
                sh 'mvn compile';
            }
        }

        stage('Maven Package') {
            steps {
                echo 'Création du livrable : ';
                sh 'mvn package -DskipTests';
            }
        }

        stage('Docker Image') {
            steps {
                echo 'Création Image : ';
                sh 'docker build -t ahmedamiri13/devops:1.0.0 .';
            }
        }

        stage('Dockerhub') {
            steps {
                echo 'Push Image to dockerhub : ';
                sh 'docker login -u ahmedamiri13 -p password12';
                sh 'docker push ahmedamiri13/devops:1.0.0';
            }
        }

        stage('MVN SONARQUBE') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.java.binaries=target/classes -Dsonar.login=admin -Dsonar.password=Password12@@'
            }
        }

        stage('MOCKITO') {
            steps {
                sh 'mvn test'
            }
        }

        stage('NEXUS') {
            steps {
                sh 'mvn deploy'
            }
        }



  stage('Mail Notification') {
            steps {
                script {
                    mail(
                        to: 'amiiri.ahmed@gmil.com',
                        subject: "Jenkins Job - Devops - Build #42 Status: SUCCESS",
                        body: """\
                        Hi Team,

                        The Jenkins job "Devops" build #42 has completed.

                        - Job URL: http://192.168.50.4:8080/job/Devops/42/
                        - Status: SUCCESS

                        Thank you,
                        Jenkins
                        """
                    )
                }
            }
        }

        stage('Docker-Compose') {
            steps {
                echo 'Staet Backend + DB : ';
                sh 'docker compose up -d';
            }
        }

    }
    post {
        failure {
            mail(
                to: 'amiiri.ahmed@gmil.com',
                subject: "Jenkins Job - Devops - Build #42 Failed",
                body: """\
                The Jenkins job "Devops" build #42 failed.

                Please check the logs at: http://192.168.50.4:8080/job/Devops/42/

                Thank you,
                Jenkins
                """
            )
        }
    }
}


