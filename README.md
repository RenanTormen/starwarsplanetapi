# starwarsplanetapi

API Em Java para fazer operações no banco de dados usando planetas do StarWars como modelo.

Utiliza também a API (https://swapi.dev/) para recuperar dados.

Projeto criado pelo Maven, e testado no servidor de aplicação Payara (versão 5).

É possível executar a API sem a necessidade de um servidor de aplicação dedicado pelo uberjar que contém uma instância do Payara-micro.

Exemplo de inicialização:

```
java -jar .\starwarsplanetapi-1.0-SNAPSHOT-microbundle.jar
```

O paraya-micro irá se encarregar de encontrar uma porta http disponível para comunicação. caso seja necessário executar em uma porta específica, deve-se utilizar o parâmetro --port <numero_da_porta> na linha de comando, e remover o --autoBindHttp do pom.xml

A geração do jar ficará disponível através da fase Install do maven, exemplo:

```
mvn clean install
```

Após execução, é possível encontrar o jar na pasta /target na raiz do projeto.

Utiliza o banco de dados H2 Integrado, com EclipseLink de PersistenceProvider, banco de dados este podendo ser facilmente configurado pelo persistence.xml e apontado para um banco externo.

No caso de utilização do uberjar para execução, o banco de dados também deve ser configurado no arquivo webapp/WEB-INF/web.xml

Para testes de integração com a camada de dados, foi utilizado banco de dados H2 e Weld-Junit para CDI.

Foram utilizados testes unitários com Weld-Junit e Mockito, para testar o Resource.

Foram realizados também testes unitários no Resource com a integração com o banco de dados.

Foi incluso Flyway para versionamento de scripts/banco de dados.

O projeto contém um arquivo chamado planeta.http com exemplo de requests feitas, podendo ser utilizado no plugin Rest Client do Visual Studio Code (https://marketplace.visualstudio.com/items?itemName=humao.rest-client).
