import json

def cognito_pre_sign_up.handler(event, context):

    # Confirm
    event['response']['autoConfirmUser'] = True

    # Set email as verified
    if 'email' in event['request']['userAttributes']:
        event['response']['autoVerifyEmail'] = True

    return event