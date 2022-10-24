import { Stage, Stack } from 'aws-cdk-lib'
import { Construct } from 'constructs'
import { CfnInternetGateway } from 'aws-cdk-lib/aws-ec2'

export interface InternetGatewayStackProps {
    accountId:    string,
    region:       string,
    stackId:      string,
    id:           string,
    vpcId:        string,
}

export class InternetGatewayStack extends Stack {
    private readonly _internetGateway: CfnInternetGateway;

    
    constructor(scope: Construct, props: InternetGatewayStackProps) {
        super(scope, props.stackId, { env: {
                account: props.accountId,   
                region: props.region,
        }});

        this._internetGateway = new CfnInternetGateway(this, props.id, {
            tags:[ {
                key: 'vpcId',
                value: props.vpcId,
            }],
        });
    }
    public get internetGateway() {
        return this._internetGateway;
    }
}