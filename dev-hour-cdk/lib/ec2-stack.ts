import { CfnKeyPair, Instance, InstanceType, MachineImage, SubnetType, Vpc, VpcEndpoint } from 'aws-cdk-lib/aws-ec2'
import { Stack } from 'aws-cdk-lib'
import { Construct } from 'constructs'
import { SecurityGroup, Peer, Port } from 'aws-cdk-lib/aws-ec2'
import { Role } from 'aws-cdk-lib/aws-iam'
import { Roles } from './roles'

export interface TableProps {
    account:      string,
    instanceName: string,
    id:           string,
    accountId:    string,
    region:       string,
    stackId:      string,
    keyName:      string,
    vpc:          Vpc,
    resourceArns: string[],
}

export class EC2Stack extends Stack {

    private readonly _ec2: Instance;
    private readonly _vpe: VpcEndpoint;
    private readonly _kp:  CfnKeyPair;
    private readonly securityGroup: SecurityGroup;
    
    constructor(scope: Construct, props: TableProps) {
        super(scope, props.stackId, { env: {
                account: props.accountId,
                region: props.region,
        }});

        this.securityGroup = new SecurityGroup(this, `${props.id}SecurityGroup`, {
            vpc: props.vpc,
            allowAllOutbound: true
        });

        this.securityGroup.addIngressRule(
            Peer.anyIpv4(),
            Port.tcp(22),
            'SSH Access'
        );

        this.securityGroup.addIngressRule(
            Peer.anyIpv4(),
            Port.tcp(443),
            'HTTPS'
        );

        this._ec2 = new Instance(this, props.id, { 
            vpc: props.vpc,
            vpcSubnets: {
                subnetType: SubnetType.PUBLIC, 
            },
            role: new Roles.EC2.IngestionRole(this, props.resourceArns),
            instanceName: props.instanceName,
            securityGroup: this.securityGroup,
            instanceType: new InstanceType('t2.micro'),
            machineImage: MachineImage.latestAmazonLinux(),
            keyName: props.keyName
        });

    }

}