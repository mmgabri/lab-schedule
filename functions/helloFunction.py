import json

def lambda_handler(event, context):
    print('Lambda Hello trigado!')

    print (f'Evento: {event}')

    return {
        
        "statusCode": 200,
        "body": json.dumps({
            "message": "Processamento efetuado com sucesso!",
        }),
    }