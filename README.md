<div align="center">
  <br />
    <a href="#" target="_blank">
      <img width="1248" height="720" src="https://github.com/user-attachments/assets/dc6116bc-a9ac-4a3d-9584-139a83590926" alt="Project Banner">
    </a>
  <br />
  <br />
  <div>
    <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F.svg?style=for-the-badge&logo=Spring-Boot&logoColor=white" alt="SpringBoot" />
    <img src="https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB" alt="ReactJS" />
    <img src="https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white" alt="Apache Maven" />
    <img src="https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" />
    <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" alt="Docker" />
  </div>
<br/><br/>
 
  <h1 align="center">Let Me Ask (the AI)</h1>

   <div align="center">
      A Q&A Room's System powered by A.I.
   </div>
</div>

## 📋 <a name="table">Sumary</a>

1. 🚀 [Introduction](#introduction)
2. ⚙️ [Tech Stack](#tech-stack)
3. 🔋 [Features](#features)
4. 💻 [Quick Start](#quick-start)
5. 💾 [Environment Variables](#envs)
6. 📅 [Releases](#versions)
7. 🤝 [Contributing](#contributing)
8. 👥 [Authors](#authors)

## <a name="introduction">🚀 Introduction</a>

An application designed to help live streamers answers the audience with the aid of AI.

## <a name="tech-stack">⚙️ Tech Stack</a>

<table text-align="center">
  <thead>
    <tr>
      <th>TOOL</th>
      <th>BACK</th>
      <th>FRONT</th>
    </tr>
  </thead>
  <tbody style="text-align:center;">
    <tr>
      <td>Java 21</td>
      <td>✅</td>
      <td></td>
    </tr>
    <tr>
      <td>Spring Boot 3</td>
      <td>✅</td>
      <td></td>
    </tr>
    <tr>
      <td>Lombok</td>
      <td>✅</td>
      <td></td>
    </tr>
    <tr>
      <td>Spring Data JPA</td>
      <td>✅</td>
      <td></td>
    </tr>
    <tr>
      <td>PostgreSQL 17</td>
      <td>✅</td>
      <td></td>
    </tr>
    <tr>
      <td>NodeJS 21</td>
      <td></td>
      <td>✅</td>
    </tr>
    <tr>
      <td>ReactJS</td>
      <td></td>
      <td>✅</td>
    </tr>
    <tr>
      <td>Vite</td>
      <td></td>
      <td>✅</td>
    </tr>
    <tr>
      <td>Zod</td>
      <td></td>
      <td>✅</td>
    </tr>
    <tr>
      <td>React Hook Form</td>
      <td></td>
      <td>✅</td>
    </tr>
    <tr>
      <td>Day.js</td>
      <td></td>
      <td>✅</td>
    </tr>
    <tr>
      <td>Axios</td>
      <td></td>
      <td>✅</td>
    </tr>
    <tr>
      <td>Tanstack React Query</td>
      <td></td>
      <td>✅</td>
    </tr>
    <tr>
      <td>NginX</td>
      <td></td>
      <td>✅</td>
    </tr>
    <tr>
      <td>Apache Maven</td>
      <td>✅</td>
      <td></td>
    </tr>
    <tr>
      <td>Docker</td>
      <td>✅</td>
      <td>✅</td>
    </tr>
  </tbody>
</table>

## <a name="features">🔋 Features</a>

- [x] Can create a new room.
- [x] Can upload audio for use while generate responses using A.I.
- [x] Can retrieve data about a room.
- [x] Can post question on a room.
- [x] Can receive response from A.I. based on audio transcriptions and content similarity.
- [x] Can manually answer a question when respnse by A.I. it's not available.
- [x] If an answer has a low grade similarity with the question, a form for user answes should be visible.

## <a name="quick-start">💻 Quick Start</a>

**00 - Prerequisites**

To use this project you must have previously setup the following:

- [x] [Git](#)
- [x] [Docker](#)

**01 - Cloning the Repository**

```bash
git clone https://github.com/salomovs95/let-me-ask
cd let-me-ask
```

**02 - Running the Project**

```bash
docker compose up -d
# or rather
docker-compose up -d
```

Open [localhost:3333](localhost:3333) in your browser to view the project to test the application.
Please, check the port.

## <a name="envs">💾 Environment Variables</a>

> [!WARNING]
> Make sure those variables are available before start the application

<details>
  <summary><code>Database Service</code></summary>

  ```yaml
  # Those are already defined at container level
  POSTGRES_USER: CHANGE_ME_LATER
  POSTGRES_PASSWORD: CHANGE_ME_LATER
  POSTGRES_DB: CHANGE_ME_LATER
  ```
</details>

<details>
  <summary><code>Backend Service</code></summary>
  
  ```yaml
  # Those are already defined at container level
  # No need to modify
  DATABASE_URL: CHANGE_ME_LATER
  DATABASE_USERNAME: CHANGE_ME_LATER
  DATABASE_PASSWORD: CHANGE_ME_LATER

  # Change only if deploying to production, otherwise no need to modify
  SPRING_PROFILE: CHANGE_LATER
  ALLOWED_ORIGINS: CHANGE_ME_LATER

  # Needed for A.I anseer generatiom, must provide ye own key
  # See how to generate one at https://aistudio.google.com/app/apikey
  GOOGLE_API_KEY: CHANGE_ME_LATER
  ```
</details>

<details>
  <summary><code>Frontend Service</code></summary>
  
  ```yaml
  # No need to modify, unless deploying each service separately
  VITE_API_URL: CHANGE_ME_ATER
  ```
</details>

## <a name="versions">📅 Releases</a>
- [ ] **TBD**

## <a name="contributing">🤝 Contributing</a>

Contributions, issues, and feature requests are welcome!

1. Fork it (<https://github.com/salomovs95/let-me-ask>)
2. Create your feature branch (`git switch -c feature/fooBar`)
3. Add your changes to the stage (`git add CHANGEDFILES`)
4. Commit your changes (`git commit -m 'Add some fooBar'`)
5. Push to the branch (`git push origin feature/fooBar`)
6. Create a new PR (Pull Request)

## <a name="authors">👥 Authors</a>

<table style="border-collapse: collapse; table-layout: auto text-align: left;">

  <tbody>
    <tr>
      <td style="padding: 10px; border: 1px solid #ddd;">
        <img src="https://avatars.githubusercontent.com/u/170432574?v=4" width="60" style="border-radius: 50%; display: block; margin: 0 auto;">
      </td>
      <td style="padding: 10px; border: 1px solid #ddd;">Salomao Souza</td>
      <td style="padding: 10px; border: 1px solid #ddd;">
        <a href="https://linkedin.com/in/salomovs95" target="_blank">LinkedIn</a> |
        <a href="https://github.com/salomovs95" target="_blank">GitHub</a>
      </td>
    </tr>
  </tbody>
</table>

> [!NOTE]
> Made in the NLW Agents (20° Edition by Rocketseat) 💜
