# Função Lambda para schedular execução de outro lambda

## Para testar Local:
### 1- criar lambda "helloFunction" no ambiente aws utilizando o códido python na pasta functions
### 2- criar role, conforme "role-eventbridge.json"
### 3- atualizar variaveis na classe ScheduleEventImpl (scheduleFunction), onde ARN é arn do lambda helloFunction e ROLE_ARN é a arn da role criada no step anterior 
### 4- subir o docker
### 5- na pasta raiz, executar o comando "sam build" (necessário ter o SAM instalado)
### 6- executar lambda scheduleFunction
### 7- verificar execução do lambda hello function no ambiente aws, de acordo com o horário schedulado


