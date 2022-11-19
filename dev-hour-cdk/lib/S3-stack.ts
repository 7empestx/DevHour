import { Construct } from 'constructs'
import { Stack } from 'aws-cdk-lib'
import { Bucket } from 'aws-cdk-lib/aws-s3'

export interface TableProps {
    account:      string,
    instanceName: string,
    id:           string,
    accountId:    string,
    region:       string,
    stackId:      string,
    keyName:      string,
}

export class S3Stack extends Stack {
    private readonly _s3: Bucket;

    constructor(scope: Construct, props: TableProps) {
        super(scope, props.id, { env: {
            account: props.accountId,
            region: props.region,
        }});
        
        this._s3 = new Bucket(this, props.id, {
            bucketName: props.id,
        });
    }

    get bucketArn(): string {
        return this._s3.bucketArn;
    }
}