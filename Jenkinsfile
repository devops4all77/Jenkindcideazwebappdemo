import jenkins.model.*

def getFtpPublishProfile(def publishProfilesJson) {
  def pubProfiles = new JsonSlurper().parseText(publishProfilesJson)
  for (p in pubProfiles)
    if (p['publishMethod'] == 'FTP')
      return [url: p.publishUrl, username: p.userName, password: p.userPWD]
}

node {
  withEnv(['AZURE_SUBSCRIPTION_ID=e44c9379-2fc4-4ec0-8945-b7fd3ff798bd',
        'AZURE_TENANT_ID=0c508b0d-c4b2-4a38-93c7-5947fb5a5656']) {
    stage('init') {
      checkout scm
    }
  
    stage('build') {
      sh 'mvn clean install'
    }
  
    stage('deploy') {
      def resourceGroup = 'jvmrg'
      def webAppName = 'webapp4jsp14312'
      // login Azure
      withCredentials([usernamePassword(credentialsId: 'eb348a15-1385-404f-9baa-4b95ea940c18', passwordVariable: 'AZURE_CLIENT_SECRET', usernameVariable: 'AZURE_CLIENT_ID')]) {
       sh '''
          az login --service-principal -u $AZURE_CLIENT_ID -p $AZURE_CLIENT_SECRET -t $AZURE_TENANT_ID
          az account set -s $AZURE_SUBSCRIPTION_ID
        '''
      }
      // get publish settings
      def pubProfilesJson = sh script: "az webapp deployment list-publishing-profiles -g $resourceGroup -n $webAppName", returnStdout: true
      def ftpProfile = getFtpPublishProfile pubProfilesJson
      // upload package
      sh "curl -T target/spring-boot-docker-complete-0.0.1-SNAPSHOT.jar $ftpProfile.url/webapps/ROOT.war -u '$ftpProfile.username:$ftpProfile.password'"
      // log out
      sh 'az logout'
    }
  }
}
