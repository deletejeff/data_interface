#/bin/sh
nohup /home/mc/jdk1.8.0_211/bin/java -jar /home/mc/data_interface-0.0.1-SNAPSHOT.jar > logs/start.log &
tail -f logs/start.log
