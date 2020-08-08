# starwarsplanetapi

API Em Java para fazer operações no banco de dados usando planetas do StarWars como modelo.

Utiliza também a API (https://swapi.dev/) para recuperar dados.

Projeto criado pelo Maven, e testado no servidor de aplicação Payara (versão 5).

Utiliza o banco de dados H2 Integrado, com EclipseLink de PersistenceProvider, banco de dados este podendo ser facilmente configurado pelo persistence.xml e apontado para um banco externo.

Para testes de integração com a camada de dados, foi utilizado banco de dados H2 e Weld para CDI.

No projeto também foi incluso Flyway para versionamento de scripts/banco de dados.

Foram utilizados testes unitários com Weld e Mockito, para testar o Resource.

O projeto contém um arquivo chamado planeta.http com todas as requests feitas, podendo ser utilizado no plugin Rest Client do Visual Studio Code (https://marketplace.visualstudio.com/items?itemName=humao.rest-client).
