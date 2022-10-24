import { Stage, Stack } from 'aws-cdk-lib'
import { Construct } from 'constructs'
import { Certificate, CertificateValidation } from 'aws-cdk-lib/aws-certificatemanager'
import { HostedZone } from 'aws-cdk-lib/aws-route53'

export interface CertificateStackProps {
    accountId:    string,
    region:       string,
    stackId:      string,
    id:           string,
    domainName:   string,
    hostedZone:   HostedZone,
}

export class CertificateStack extends Stack {
    private readonly _certificate: Certificate;

    
    constructor(scope: Construct, props: CertificateStackProps) {
        super(scope, props.stackId, { env: {
                account: props.accountId,   
                region: props.region,
        }});
        
        const wildcard = `*.${props.domainName}`;

        this._certificate = new Certificate(this, props.id, {
            domainName:                 props.domainName,
            subjectAlternativeNames:    [ wildcard ], 
            validation:                 CertificateValidation.fromDns(props.hostedZone),
        });

    }

    public get certificate() {
        return this._certificate;
    }

}