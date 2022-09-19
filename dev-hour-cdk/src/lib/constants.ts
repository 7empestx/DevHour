/**
 * Constants File. Defines project-wide constants.
 * @version 0.1.0
 */

import { SubnetType } from '@aws-cdk/aws-ec2'

export module Constants {

    /// ---
    /// App
    
    export const Account = ''		;
    export const Region  = 'us-west-1'  ;
    export const AppName = 'DevHour'    ;

    /// ------
    /// Stages

    export module Stages {

        /// -----
        /// Alpha

        export module Alpha {

            export const Name = 'Alpha'        ;
            export const Id   = `${Name}Stage` ;            

        }

    }

    /// ---
    /// EC2

    export module EC2 {

        /// ---
        /// VPC

        export module VPC {

            export const Id                 = `${AppName}VPC`             ;
            export const CIDR               = `10.0.0.0/16`               ;
            export const MaxAZs             = 2                           ;
            export const NATGateways        = 0                           ;
            export const SubnetCIDRMask     = 24                          ;
            export const VPCSubnetType      = SubnetType.PRIVATE_ISOLATED ;
            export const EnableDNSHostNames = true                        ;
            export const EnabltDNSSupport   = true                        ;
        }

    }

    /// -------------
    /// Code Pipeline

    export module CodePipeline {

        export const Id         = `${AppName}Pipeline` ;
        export const StackId    = `${Id}Stack`         ;
        export const SelfMutate = true                 ;

        /// ---------
        /// ShellStep

        export module ShellStep {

            export const Id = `${AppName}ShellStep`    ;

        }

    }

}
