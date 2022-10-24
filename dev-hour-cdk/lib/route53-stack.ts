// need to make adjustments
// Grant mentioned this was a good starting point.
// Need to make modifications.

import { Stage, Stack } from 'aws-cdk-lib'
import { Construct } from 'constructs'
import { HostedZone } from 'aws-cdk-lib/aws-route53'

export interface HostedZoneStackProps {
    accountId:    string,
    region:       string,
    stackId:      string,
    id:           string,
    domainName:   string,
}

export class HostedZoneStack extends Stack {
    private readonly _hostedZone: HostedZone;

    
    constructor(scope: Construct, props: HostedZoneStackProps) {
        super(scope, props.stackId, { env: {
                account: props.accountId,   
                region: props.region,
        }});

    this._hostedZone = HostedZone.fromLookup(this, props.id, {
        domainName: props.domainName


    }) as HostedZone ;

    }
    public get hostedZone() {
        return this._hostedZone;
    }
}

// differences:
//