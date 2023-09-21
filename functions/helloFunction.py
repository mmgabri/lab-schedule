import json
import boto3

client_sns = boto3.client('sns')
arn_topic='arn:aws:sns:us-east-1:146570171569:test-email'
message = 'Corpo da mensagem.....bla bla bla'

def send_email():
    response = client_sns.publish(
    TargetArn=arn_topic,
    Message=json.dumps({'default': json.dumps(message)}),
    MessageStructure='json'
)
    print(f'Response: {response}')

def lambda_handler(event, context):
    print('Lambda Hello trigado!')

    print (f'Evento: {event}')

    send_email()

    return {
        
        "statusCode": 200,
        "body": json.dumps({
            "message": "Processamento efetuado com sucesso!",
        }),
    }