

# **Sistema de Gerenciamento de Usinas Nucleares - SAMEN**

**Descrição do Sistema**

Este projeto é um sistema de gerenciamento de usinas nucleares, desenvolvido utilizando o framework Spring Boot para a criação de uma API RESTful, que permite o gerenciamento completo das usinas nucleares, incluindo a criação, leitura, atualização e exclusão (CRUD) das informações das usinas.
A API é auto-descritiva e navegável, utilizando o conceito de HATEOAS (Hypermedia as the Engine of Application State). Isso significa que as respostas da API incluem links para outros recursos relacionados, permitindo que os consumidores da API possam navegar facilmente para outras operações disponíveis sem precisar de documentação adicional ou conhecimento prévio sobre os endpoints.
A documentação da API está disponível via Swagger UI, uma interface visual interativa que permite testar todos os endpoints da API de maneira fácil e intuitiva. 

**Uso de HATEOAS**

O sistema utiliza o conceito de HATEOAS para enriquecer as respostas da API com links dinâmicos. Cada resposta inclui links para recursos relacionados, como o próprio recurso acessado, links para atualizações e exclusões, e outras ações possíveis no sistema.

Por exemplo, quando você recupera uma usina nuclear específica, a resposta não contém apenas os dados da usina, mas também inclui links que permitem:

Acesso aos detalhes da usina (self).
Ações que podem ser realizadas com o recurso, como atualização e exclusão.
Essa abordagem proporciona uma navegação intuitiva entre os recursos e reduz a necessidade de conhecer todos os endpoints de antemão, permitindo que a API se autodescreva e ofereça flexibilidade aos desenvolvedores que a consomem.

# **O sistema foi implementado com as seguintes funcionalidades:**

- **Listar Usinas Nucleares**: Retorna uma lista paginada das usinas nucleares cadastradas no sistema.
- **Detalhar Usina Nuclear**: Permite acessar os detalhes completos de uma usina nuclear específica através de seu ID.
- **Criar Usina Nuclear**: Adiciona uma nova usina ao sistema.
- **Atualizar Usina Nuclear**: Atualiza as informações de uma usina existente.
- **Excluir Usina Nuclear**: Remove uma usina do sistema.

# **Funcionalidades da API**

Abaixo estão os exemplos de testes para cada operação CRUD, formatados em tabela, com informações sobre **requisições** e **respostas** no **Postman**.


| **Operação**                  | **Método HTTP** | **URL**                                               | **Descrição**                                               |
|-------------------------------|-----------------|-------------------------------------------------------|-------------------------------------------------------------|
| **Listar Usinas Nucleares**    | `GET`           | `http://localhost:8080/api/nuclear-plants?page=0&size=10` | Retorna a lista das usinas nucleares cadastradas.      |
| **Criar Usina Nuclear**        | `POST`          | `http://localhost:8080/api/nuclear-plants`             | Cria uma nova usina nuclear com nome, capacidade e número de reatores.|
| **Detalhar Usina Nuclear**     | `GET`           | `http://localhost:8080/api/nuclear-plants/{id}`        | Detalha uma usina específica, identificado pelo ID.        |
| **Atualizar Usina Nuclear**    | `PUT`           | `http://localhost:8080/api/nuclear-plants/{id}`        | Atualiza os dados de uma usina existente.                  |
| **Excluir Usina Nuclear**      | `DELETE`        | `http://localhost:8080/api/nuclear-plants/{id}`        | Exclui uma usina pelo ID fornecido.                        |

## **Uso de Métricas no Sistema**

### **Métricas Operacionais**

O sistema gerencia dados operacionais das usinas nucleares registrados na entidade `Metric`. Essas métricas fornecem informações sobre o desempenho e eficiência de cada usina.  

**Atributos de cada métrica:**
- **Data da Métrica (`metricDate`)**: Data e hora do registro da métrica.
- **Energia Fornecida (`electricityProvided`)**: Total de energia gerada pela usina (em MW).
- **Participação Nuclear (`nuclearParticipation`)**: Porcentagem da participação da usina no fornecimento total de energia.
- **Eficiência Operacional (`operationalEfficiency`)**: Índice que mede a eficiência da operação da usina (em %).

### **Operações CRUD para Métricas**

| **Operação**                  | **Método HTTP** | **URL**                                               | **Descrição**                                               |
|-------------------------------|-----------------|-------------------------------------------------------|-------------------------------------------------------------|
| **Listar Métricas**            | `GET`           | `http://localhost:8080/api/metrics?page=0&size=10`    | Retorna uma lista paginada de métricas cadastradas.         |
| **Criar Métrica**              | `POST`          | `http://localhost:8080/api/metrics`                   | Adiciona uma nova métrica operacional para uma usina.       |
| **Detalhar Métrica**           | `GET`           | `http://localhost:8080/api/metrics/{id}`              | Detalha uma métrica específica, identificada pelo ID.       |
| **Atualizar Métrica**          | `PUT`           | `http://localhost:8080/api/metrics/{id}`              | Atualiza os dados de uma métrica existente.                 |
| **Excluir Métrica**            | `DELETE`        | `http://localhost:8080/api/metrics/{id}`              | Exclui uma métrica pelo ID fornecido.                       |


## **Algumas imagens de como ficou a aplicação mobile do projetos**

**Essa é a tela de login**

![tela de login](https://github.com/bia98silva/SAMEN/blob/main/imagens/tela_login%20.png)

**Essa é a tela de Monitoramento**

![tela de login](https://github.com/bia98silva/SAMEN/blob/main/imagens/monitoramento.png)


**Essa é a tela de cadastro**
![tela de login](https://github.com/bia98silva/SAMEN/blob/main/imagens/cadastro.png)

**Essa é a tela de radiação**
![tela de login](https://github.com/bia98silva/SAMEN/blob/main/imagens/radiacao.png)

**Essa é a tela de temperatura**
![tela de login](https://github.com/bia98silva/SAMEN/blob/main/imagens/temperatura.png)

**Essa é a tela de pressão**

![tela de login](https://github.com/bia98silva/SAMEN/blob/main/imagens/Pressao.png)

**Essa é a tela de modificar cadastro**

![tela de login](https://github.com/bia98silva/SAMEN/blob/main/imagens/modificar_cadastro.png)



## Tecnologias Utilizadas

- **Spring Boot**: Framework Java para construção de APIs RESTful.
- **Spring Data JPA**: Para persistência e manipulação de dados no banco de dados.
- **Springdoc OpenAPI**: Para gerar a documentação automática da API.
- **Swagger UI**: Interface visual para interagir com a API e testar os endpoints.
- **HATEOAS**: Para fornecer links dinâmicos nas respostas da API, permitindo a navegação entre recursos de forma flexível.
- **Postman**: Ferramenta para testar e validar os endpoints da API.

Link do Pitch: https://www.youtube.com/watch?v=QuJXTu1zSBA
##
Link do video da aplicação no Docker: https://www.youtube.com/watch?v=N0bCZkCKITY

# **Integrantes:** 
Beatriz silva RM: 552600 
Pedro Henrique soares araujo RM: 553801 
Vitor Onofre Ramos RM:553241 
