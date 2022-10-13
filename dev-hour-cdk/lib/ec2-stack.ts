import { Instance, InstanceType, MachineImage, SubnetType, Vpc, VpcEndpoint } from 'aws-cdk-lib/aws-ec2'
import { Stack } from 'aws-cdk-lib'
import { Construct } from 'constructs'

export interface TableProps {
    account:      string,
    instanceName: string,
    id:           string,
    accountId:    string,
    region:       string,
    stackId:      string,
    vpc:          Vpc,
}

export class EC2Stack extends Stack {
    private readonly _ec2: Instance;
    private readonly _vpe: VpcEndpoint;
    
    constructor(scope: Construct, props: TableProps) {
        super(scope, props.stackId, { env: {
                account: props.accountId,
                region: props.region,
        }});

        this._vpe = props.vpc.addInterfaceEndpoint('EC2-Ingestion-Endpoint', {
            service: { name: 'com.amazonaws.' + props.region + '.ec2', port: 443 },
            privateDnsEnabled: true,
            subnets: { subnetType: SubnetType.PRIVATE_ISOLATED },
        });

        this._ec2 = new Instance(this, props.id, { 
            vpc: props.vpc,
            vpcSubnets: {
                subnetType: SubnetType.PRIVATE_ISOLATED, 
            },
            instanceName: props.instanceName,
            instanceType: new InstanceType('t2.micro'),
            machineImage: MachineImage.latestAmazonLinux()
        });
    }
}