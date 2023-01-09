<h1>Java 11 | Selenium | TestNG | Maven | POM Project</h1>
<p>This is a sample Java 11 AdoptOpenJDK | Selenium WebDriver | Maven | TestNG project created in IntelliJ IDE, using Page Object Model and Generic Type.</p>
<p>Website <a href="https://www.jenkins.io/">https://https://www.jenkins.io/</a>&nbsp;was used to create functional, and UI tests.</p>
<p><a href="https://github.com/Iriskus/JenkinsJava/blob/main/.github/workflows/ci.yml">ci.yml</a> file was used for the GitHub workflow<br /><br /><a href="https://github.com/dorny/test-reporter">dorny/test-reporter@v1</a> was used to generate reports after each CI run<br /><br /></p>
<p><strong>pom.xml dependencies used:</strong></p>
<blockquote>
<pre>&lt;dependencies&gt;<br /><br />    &lt;dependency&gt;<br />        &lt;groupId&gt;org.testng&lt;/groupId&gt;<br />        &lt;artifactId&gt;testng&lt;/artifactId&gt;<br />        &lt;version&gt;7.6.1&lt;/version&gt;<br />    &lt;/dependency&gt;<br /><br />    &lt;dependency&gt;<br />        &lt;groupId&gt;org.seleniumhq.selenium&lt;/groupId&gt;<br />        &lt;artifactId&gt;selenium-java&lt;/artifactId&gt;<br />        &lt;version&gt;4.5.3&lt;/version&gt;<br />    &lt;/dependency&gt;<br /><br />    &lt;dependency&gt;<br />        &lt;groupId&gt;io.github.bonigarcia&lt;/groupId&gt;<br />        &lt;artifactId&gt;webdrivermanager&lt;/artifactId&gt;<br />        &lt;version&gt;5.3.0&lt;/version&gt;<br />    &lt;/dependency&gt;<br /><br />&lt;/dependencies&gt;</pre>
</blockquote>
<h1>Setup the project and execute tests locally</h1>
<p>1. Install IntelliJ IDE:<br /><a href="https://www.jetbrains.com/help/idea/installation-guide.html">https://www.jetbrains.com/help/idea/installation-guide.html</a></p>
<p>2. Copy the HTTPS project link from the GitHub repository:&nbsp;<br /><a href="https://github.com/Iriskus/JenkinsJava.git">https://github.com/Iriskus/JenkinsJava.git</a></p>
<p>3. Clone a repository from the main menu:&nbsp;<br /><a title="https://www.jetbrains.com/help/idea/cloning-repository.html#clone_project_from_main_screen" href="https://www.jetbrains.com/help/idea/cloning-repository.html#clone_project_from_main_screen">https://www.jetbrains.com/help/idea/cloning-repository.html#clone_project_from_main_screen</a></p>
<p>4. Go to the resources package, and copy local.properties.TEMPLATE file. Paste it to the resources package, and re-name the new file as&nbsp;local.properties</p>
<p>5. Execute test class or single test by opening the Test class, right-clicking on the green triangle, and choosing Run</p>
