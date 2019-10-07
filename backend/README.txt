Open terminal and go to backend folder in project directory.
Type
--- mvn clean install package
This command generates a .jar file in target folder.
Then you go to https://aws.amazon.com/tr/console/ and login using hatice's credentials.
After that you go to elasticbeanstalk service and open Cmpe451group10 project.
There you will see an upload and deploy button, press that and upload the .jar file.