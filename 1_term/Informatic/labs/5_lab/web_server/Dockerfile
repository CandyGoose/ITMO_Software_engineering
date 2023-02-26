FROM ubuntu:latest

RUN apt-get update
RUN apt-get install -y apache2

EXPOSE 80

RUN echo "<html> <head>   <title> I love Docker! </title> </head> <body>  <p> Hello world! I'm Vera and I study at ITMO University! </p> </body> </html>" > /var/www/html/index.html

CMD ["apache2ctl", "-D", "FOREGROUND"]
