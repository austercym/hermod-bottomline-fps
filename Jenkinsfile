@Library('jenkins-shared-libs@feature/lib_refactor')_

pipeline {

    agent { label 'docker'}

    environment {
        ARTIFACTORY_SERVER_REF = 'artifactory'

        artifactVersion = "${new Date().format('yy.MM.dd')}"
        pomPath = 'pom.xml'

        snapshotRepository = 'libs-snapshot-local'
        releaseRepository = 'libs-release-local'
        snapshotDependenciesRepository = 'libs-snapshot'
        releaseDependeciesRepository = 'libs-release'


    }

    stages {

        stage('Pipeline setup') {
            parallel {
                stage('Triggers setup') {
                    agent{
                        docker {
                            image 'maven:3-alpine'
                            args '-v $HOME/.m2:/root/.m2 --network user-default'
                            reuseNode true
                            label 'docker'
                        }
                    }
                    steps {
                        script {
                            withCredentials([string(credentialsId: 'github-orwell-cicd-webhook-token', variable: 'githubWebhookGenericToken')]) {
                                properties([
                                        pipelineTriggers([
                                                [
                                                        $class                   : 'GenericTrigger',
                                                        causeString              : 'Push made',
                                                        token                    : githubWebhookGenericToken,
                                                        genericHeaderVariables   : [
                                                                [key: 'X-GitHub-Event', regexpFilter: '']
                                                        ],
                                                        genericVariables         : [
                                                                [key: 'project', value: '$.repository.name'],
                                                                [key: 'branch', value: '$.ref']
                                                        ],
                                                        regexpFilterExpression   : (env.JOB_NAME.tokenize('/'))[0] + ',push',
                                                        regexpFilterText         : '$project,$x_github_event',
                                                        printContributedVariables: true,
                                                        printPostContent         : true
                                                ]
                                        ])
                                ])
                            }
                        }
                    }
                }

                stage('Artifactory setup') {
                    agent{
                        docker {
                            image 'maven:3-alpine'
                            args '-v $HOME/.m2:/root/.m2 --network  user-default'
                            reuseNode true
                            label 'docker'
                        }
                    }
                    steps {
                        script {
                            def MAVEN_HOME = sh(script: 'echo $MAVEN_HOME', returnStdout: true).trim()
                            // Obtain an Artifactory server instance, defined in Jenkins --> Manage:
                            server = Artifactory.server ARTIFACTORY_SERVER_REF

                            def  descriptor = Artifactory.mavenDescriptor()
                          //  def end = '-SNAPSHOT'
                            descriptor.pomFile = pomPath
                            def scmVars = checkout scm // this Line not exist in old versions
                           // if (!descriptor.version.endsWith('-SNAPSHOT')) This is the old if condition in projects we need subtitute by the next line
                            if (!( scmVars.GIT_BRANCH == 'master')) // CORRECT LINE
                                artifactVersion = artifactVersion + '-SNAPSHOT'
                            descriptor.version = artifactVersion
                            descriptor.transform()

                            rtMaven = Artifactory.newMavenBuild()
                            env.MAVEN_HOME = MAVEN_HOME
                            rtMaven.deployer releaseRepo: releaseRepository, snapshotRepo: snapshotRepository, server: server
                            rtMaven.resolver releaseRepo: releaseDependeciesRepository, snapshotRepo: snapshotDependenciesRepository, server: server
                            rtMaven.opts = '-DprofileIdEnabled=true'
                            rtMaven.deployer.deployArtifacts = false // Disable artifacts deployment during Maven run

                            buildInfo = Artifactory.newBuildInfo()
                        }
                    }
                }
            }
        }

        stage('Unit test') {
            agent{
                docker {
                    image 'maven:3-alpine'
                    args '-v $HOME/.m2:/root/.m2 --network user-default'
                    reuseNode true
                    label 'docker'
                }
            }
            steps {

                    script {
                        rtMaven.run pom: pomPath, goals: '-U clean test'
                    }

            }

            post {
                  always {
                      junit 'target/surefire-reports/*.xml'
                  }
            }
        }

        stage('Build') {
            agent{
                docker {
                    image 'maven:3-alpine'
                    args '-v $HOME/.m2:/root/.m2 --network user-default'
                    reuseNode true
                    label 'docker'
                }
            }
            steps {
                script {
                    rtMaven.run pom: pomPath, goals: '-U clean package -DskipTests', buildInfo: buildInfo
                }
            }

            post {
                always {
                    archiveArtifacts artifacts: '**/target/*.jar'
                }
            }
        }

        stage('Publish') {
            agent{
                docker {
                    image 'maven:3-alpine'
                    args '-v $HOME/.m2:/root/.m2 --network user-default'
                    reuseNode true
                    label 'docker'
                }
            }

            steps {
                script {
                    server.publishBuildInfo buildInfo
                    rtMaven.deployer.deployArtifacts buildInfo
                }

            }
        }

        stage('Remove_Workspace') {
            steps {
                deleteDir()
            }
        }


    }
}