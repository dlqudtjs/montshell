FROM python:3
RUN mkdir /app
WORKDIR /app
COPY main.py /app
CMD python Main.py
