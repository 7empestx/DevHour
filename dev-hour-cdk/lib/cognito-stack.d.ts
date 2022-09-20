import { Stack } from 'aws-cdk-lib';
import { Construct } from 'constructs';
import { Role } from 'aws-cdk-lib/aws-iam';
export interface CognitoProps {
    account: string;
    region: string;
    userPoolID: string;
    identityPoolID: string;
    stackID: string;
    identityProviderID: string;
    userPoolClientID: string;
    userPoolClientSecret: string;
    authenticatedRole: Role;
    unauthenticatedRole: Role;
}
export declare class CognitoStack extends Stack {
    private readonly userPool;
    private readonly identityPool;
    private readonly userPoolClient;
    private readonly userPoolIdentityProviderAmazon;
    constructor(scope: Construct, props: CognitoProps);
}
