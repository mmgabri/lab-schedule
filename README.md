# Função Lambda para schedular execução de outro lambda

## Para testar Local:
> criar lambda "helloFunction" no ambiente aws utilizando o códido python na pasta functions
> criar role, conforme "role-eventbridge.json"
> atualizar variaveis na classee ScheduleEventImpl (scheduleFunction), onde ARN é arn do lambda helloFunction e ROLE_ARN é a arn da role criada no step anterior 
> subir o docker
> na pasta raiz, executar o comando "sam build" (necessário ter o SAM instalado)
> executar lambda


