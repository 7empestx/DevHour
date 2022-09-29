import { Table } from 'aws-cdk-lib/aws-dynamodb'
import { Stack } from 'aws-cdk-lib'
import { Construct } from 'constructs'

export interface TableProps {

}

export class dynamoDBStack extends Stack {
    private readonly _db: Table;
    
    constructor(scope: Construct, id: string, props: TableProps) { 
        super();
   }

   public get table() {
        return this._db;
    }
}

