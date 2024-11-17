1) TO CHECK JAVA PATH : readlink -f $(which java)
2) add this path to env variable : 
	open bash file :  nano ~/.bashrc
	paste this : 
	export JAVA_HOME=/usr/local/jdk-9
    export PATH=$JAVA_HOME/bin:$PATH
	


3) apply changes : source ~/.bashrc
4) check configuration : 
	echo $JAVA_HOME
	echo $PATH


5) HADOOP PATH : 
export HADOOP_HOME=~/hadoop/hadoop-3.4.0
export PATH=$PATH:$HADOOP_HOME/bin


6) Compile & Run 
	i) hadoop com.sun.tools.javac.Main -Xlint MaxTemperature.java MaxTemperatureMapper.java MaxTemperatureReducer.java
	ii) archieve all class in jar - 
		jar cf wc.jar MaxTemperature*.class
	iii) run jar file : 
		hadoop jar wc.jar MaxTemperature Input/1901 output1
		

1)compile : hadoop com.sun.tools.javac.Main -Xlint MovieGenreCount.java MovieGenreMapper.java MovieGenreReducer.java

2) archieve into jar : jar cf genrecount.jar MovieGenreCount*.class MovieGenreMapper*.class MovieGenreReducer*.class

3) run : hadoop jar genrecount.jar MovieGenreCount Input/input.txt output1

4)see output ;  hadoop fs -cat output1/part-r-00000
