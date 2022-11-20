import { Construct } from 'constructs'
import { Stack } from 'aws-cdk-lib'
import { Bucket } from 'aws-cdk-lib/aws-s3'

export interface TableProps {
    account:      string,
    bucketName:   string,
    accountId:    string,
    id:           string,
    region:       string,
    stackId:      string,
}

export class S3Stack extends Stack {
    private readonly _s3: Bucket;

    constructor(scope: Construct, props: TableProps) {
        super(scope, props.stackId, { env: {
            account: props.accountId,
            region: props.region,
        }});
        
        this._s3 = new Bucket(this, props.id, {
            bucketName: props.bucketName,
        });
    }

    get bucketArn(): string {
        return this._s3.bucketArn;
    }
}