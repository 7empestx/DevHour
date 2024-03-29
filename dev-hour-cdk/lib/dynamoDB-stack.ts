import { Attribute, Table } from 'aws-cdk-lib/aws-dynamodb'
import { Stack } from 'aws-cdk-lib'
import { Construct } from 'constructs'

export interface TableProps {
    partitionKey: Attribute,
    tableName:    string,
    stackId:      string,
    id:           string,
    accountId:    string, 
    region:       string,
}

export class DynamoDBStack extends Stack {
    private readonly _db: Table;
    
    constructor(scope: Construct, props: TableProps) { 
        super(scope, props.stackId, { env: {
            account: props.accountId,
            region: props.region,
        }});

        this._db = new Table(this, props.id, {
            partitionKey: props.partitionKey,
            tableName: props.tableName
        });

    } 

    get tableArn(): string {
        return this._db.tableArn;
    }
    
}

