FROM ubuntu:lucid

RUN sed -i 's/archive/old-releases/' /etc/apt/sources.list

RUN apt-get update && apt-get install -y \
    apt-utils \
    mesa-utils \
    nano \
    && rm -rf /var/lib/apt/lists/*
        
RUN mkdir -p /var/lib/apt/lists/partial && apt-get update && apt-get install -y build-essential libsdl-dev \
                        libsdl-image* \
                        libsdl-mixer* \
                        libsdl-ttf* \
                        libsdl-gfx* \
                        freetype*

# ADD ./MegaMario_v1.7_full /MegaMario_v1.7_full

COPY ./MegaMario_v1.7_full.zip /MegaMario_v1.7_full.zip

RUN unzip MegaMario_v1.7_full.zip -d MegaMario_v1.7_full

RUN cd MegaMario_v1.7_full && make PREFIX=/usr && make PREFIX=/usr install
