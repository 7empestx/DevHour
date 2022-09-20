/**
 * Constants File. Defines project-wide constants.
 * @version 0.1.0
 */
import { SubnetType } from 'aws-cdk-lib/aws-ec2';
export declare module Constants {
    const Account = "";
    const Region = "us-west-1";
    const AppName = "DevHour";
    module Stages {
        module Alpha {
            const Name = "Alpha";
            const Id: string;
        }
    }
    module EC2 {
        module VPC {
            const Id: string;
            const CIDR = "10.0.0.0/16";
            const MaxAZs = 2;
            const NATGateways = 0;
            const SubnetCIDRMask = 24;
            const VPCSubnetType = SubnetType.PRIVATE_ISOLATED;
            const EnableDNSHostNames = true;
            const EnabltDNSSupport = true;
        }
    }
    module CodePipeline {
        const Id: string;
        const StackId: string;
        const SelfMutate = true;
        module ShellStep {
            const Id: string;
        }
    }
    module DynamoDB {
        module User {
            const AllowActions: never[];
        }
        const AllowActions: string[];
    }
}
