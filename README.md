.\mvnw clean package
docker build -t jasperreports .
docker run -p 8080:8080 --name jasperreports jasperreports