# API
API RESTful para possibilitar a leitura da lista de indicados e vencedores da categoria Pior Filme do Golden Raspberry Awards

## Informações
Projeto desenvolvido seguindo como referência os requisitos solicitados, onde a API deve:
* Ler o arquivo CSV dos filmes e inserir os dados em uma base de dados ao inciar a aplicação.
* Obter o produtor com maior intervalo entre dois prêmios consecutivos, e o que obteve dois prêmios mais rápido

## Tecnologias
* Java 21
* SpringBoot(JPA, Actuator, rest, web, test, JUnit, Mock)
* H2 in Memory Database

## Configuração no ambiente de desenvolvimento local
Para rodar o projeto em ambiente local de desenvolvimento basta clonar o projeto e importar em alguma IDE (preferencialmente Intellij).

Executar a clase ``` FilmesApplication ``` para subir a API que ficará disponível em ```http://locahost/8080```

Ao executar esta classe e subir a aplicação o sistema irá rodar o Serviço ```DBLoaderRunner``` do método ```run``` importando o arquivo CSV de filmes e inserindo automaticamente na base de dados.
Esse arquivo deve estar na raiz do projeto e o seu nome deve ser inserido na variável ```CSV_FILE``` da classe ```DBLoaderRunner```.

`public static final String CSV_FILE = "movielist.csv";`

#### Executando os testes
Para rodar os testes de integração basta rodar a classe ```FilmesApplicationTests``` ou através do comando maven: ```mvn test```
Para testar o endpoint foi adicionado a variavel ```ENDPOINT``` com o endpoint do get na classe ```FilmesApplicationTests```.

`public static String ENDPOINT = "/api/filmes";`


## REST Api
O projeto contém a seguinte chamada Rests na URL base: `http://localhost:8080/api/filmes/`

## Obtêm dados vencedores
Obtêm o  produtor com  maior  intervalo  entre  dois  prêmios consecutivos,  e  o  que obteve dois prêmios mais rápido

### Request

`GET http://localhost:8080/api/filmes`

### Response
```
{
    "min": [
        {
            "producer": "Producer 1",
            "interval": 1,
            "previousWin": 2008,
            "followingWin": 2009
        },
        {
            "producer": "Producer 2",
            "interval": 1,
            "previousWin": 2018,
            "followingWin": 2019
        }
    ],
    "max": [
        {
            "producer": "Producer 1",
            "interval": 99,
            "previousWin": 1900,
            "followingWin": 1999
        },
        {
            "producer": "Producer 2",
            "interval": 99,
            "previousWin": 2000,
            "followingWin": 2099
        }
    ]
}
```

## Contato
Created by [@kelvinadrian](https://www.linkedin.com/in/kelvinadrian)
