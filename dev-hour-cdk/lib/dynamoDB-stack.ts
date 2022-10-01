import { Table } from 'aws-cdk-lib/aws-dynamodb'
import { Stack } from 'aws-cdk-lib'
import { Construct } from 'constructs'

export interface TableProps {
    tableName: string,
    id: string,
    stackId: string,
}

export class DynamoDBStack extends Stack {
    private readonly _db: Table;
    
    constructor(scope: Construct, props: TableProps) { 
        super(scope, props.tableName);

/*         this._db = new Table(this, props.id, {
            tableName:  props.tableName
        }); */
   }


    
}

