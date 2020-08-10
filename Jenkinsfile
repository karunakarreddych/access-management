#!groovy

node('chase-dev') {
    try{
    stage('checkout') {
            echo 'configure started'
            git branch: '${BRANCH_NAME}', credentialsId:'519f61d8-ec2a-4ea8-9bdd-877e99afe9e3', url: 'https://gitlab.com/invente/chase/apis/access-management-domain-apis.git'
            echo 'configure done'
            }
    stage('mvn build') {
                def mvn_version = 'M3'
                withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
                sh 'mvn clean install'
            }
        }
   
   stage('code quality') {
         sh 'mvn sonar:sonar -Dsonar.host.url=http://10.9.9.202:30009'
        } 
               

	 
}
finally{
   stage('stop-container') {
	sh 'sudo docker-compose down'
	}
}
}
