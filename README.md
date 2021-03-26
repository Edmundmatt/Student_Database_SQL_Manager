## SWEN301 Assignment 1 Template

7a.
nz.ac.wgtn.swen301.assignment1.cli.FindStudentDetails (the UI layer) depends on  nz.ac.wgtn.swen301.assignment1 (the domain layer) as outlined in the jdepend report. This is correct.

7b.
Running the mvn command 'mvn package' creates an output jar file in the /target folder with the maven-jar-plugin in the pom.xml. When one has the spring-bot-jar plugin included in the pom.xml a spring-boot-jar file is also created. These can then be executed if studentdb.jar is included in the spring-boot-jar executable.

7c.
The project is prone to memory leaks by interfering with garbage collection. The automatic garbage collection running in program IDEs like Intellij is better at handling resources than manual closing and release typically. This is not true for ResultSet(s) and PreparedStatement(s) though which which aren't managed by the IDE and should be closed manually in the program to not hog resources. Disregarding these sql interaction methods the most efficient way of dealing with garbage collection is not manually interacting with it at all. 