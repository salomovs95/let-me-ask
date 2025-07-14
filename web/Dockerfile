FROM node:22.16.0-alpine3.22 AS build

WORKDIR /app

COPY ./*.json /app
COPY vite.config.ts /app
COPY index.html /app
COPY public/ /app
COPY src/ /app

RUN npm ci && npm run build

FROM nginx:mainline-alpine3.22-slim
WORKDIR /app
COPY ./nginx.conf /etc/nginx/nginx.conf
COPY --from=build dist/* /app/*

CMD ["sudo", "nginx"]
