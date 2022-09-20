/**
 * VPC Stack
 * @version 0.9.0
 */
import { Construct } from 'constructs';
import { Stack } from 'aws-cdk-lib';
import { Vpc, SubnetConfiguration } from 'aws-cdk-lib/aws-ec2';
export interface VpcStackProps {
    account: string;
    region: string;
    id: string;
    stackId: string;
    cidr: string;
    maxAzs: number;
    natGateways: number;
    subnetConfiguration: SubnetConfiguration[];
    enableDnsHostnames: boolean;
    enableDnsSupport: boolean;
}
export declare class VpcStack extends Stack {
    private readonly _vpc;
    constructor(scope: Construct, props: VpcStackProps);
    get vpc(): Vpc;
    get privateNatSubnets(): import("aws-cdk-lib/aws-ec2").SelectedSubnets;
    get privateIsolatedSubnets(): import("aws-cdk-lib/aws-ec2").SelectedSubnets;
    get publicSubnets(): import("aws-cdk-lib/aws-ec2").SelectedSubnets;
}
