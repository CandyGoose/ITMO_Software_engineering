FROM ubuntu:10.04

RUN sed -i 's/archive/old-releases/' /etc/apt/sources.list

RUN apt-get update && apt-get install -y \
    apt-utils \
    wget \
    unzip\
    nano \
    build-essential \    
    && rm -rf /var/lib/apt/lists/*
        
RUN mkdir /var/lib/apt/lists/partial && apt-get update && apt-get install -y libsdl-dev \
                        libsdl-image* \
                        libsdl-mixer* \
                        libsdl-ttf* \
                        libsdl-gfx* \
                        freetype*

COPY ./MegaMario_v1.7_full.zip /MegaMario_v1.7_full.zip

RUN unzip MegaMario_v1.7_full.zip -d MegaMario_v1.7_full

RUN sed -i "s/PATH_MAX/1024/g" /MegaMario_v1.7_full/src/Global.h

RUN cd MegaMario_v1.7_full && make PREFIX=/usr && make PREFIX=/usr install

