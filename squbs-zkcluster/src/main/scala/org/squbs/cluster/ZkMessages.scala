package org.squbs.cluster

import akka.actor.Address
import akka.util.ByteString
import org.apache.curator.framework.CuratorFramework

/**
 * Created by huzhou on 6/9/14.
 */

/**
 * request for leader identity of the cluster
 */
case object ZkQueryLeadership

/**
 * request for members identities of the cluster
 */
case object ZkQueryMembership

/**
 * subscribe to zkclient updates
 */
case object ZkMonitorClient

/**
 * response for leader identity query
 * @param address
 */
case class ZkLeadership(address: Address)

/**
 * response for members identities query
 * @param members
 */
case class ZkMembership(members: Set[Address])

/**
 * event when zkclient updates
 * @param zkClient
 */
case class ZkClientUpdated(zkClient:CuratorFramework)

/**
 * request for partition members
 * @param partitionKey
 * @param notification notify the sender along with query result
 * @param expectedSize create the partition or resize the partition with the suggested size
 * @param props properties of the partition, plain byte array
 * @param members don't assign anything, used internally
 */
case class ZkQueryPartition(partitionKey:ByteString,
                            notification:Option[Any] = None,
                            expectedSize:Option[Int] = None,
                            props:Array[Byte] = Array[Byte](),
                            members:Set[Address] = Set.empty) {
  
  if (expectedSize.nonEmpty) require(expectedSize.get > 0)
  
}

/**
 * request to discontinue a partition
 * @param partitionKey
 */
case class ZkRemovePartition(partitionKey:ByteString)

/**
 * subscribe to partition updates
 */
case object ZkMonitorPartition

/**
 * stop subscription to a partition's updates
 */
case object ZkStopMonitorPartition

/**
 * event of partition update
 * @param partitionKey
 * @param onBoardMembers
 * @param dropOffMembers
 */
case class ZkPartitionDiff(partitionKey: ByteString, onBoardMembers: Set[Address], dropOffMembers: Set[Address])

/**
 * event of a partition removal
 * @param partitionKey
 */
case class ZkPartitionRemoval(partitionKey:ByteString)

/**
 * response for partition query
 * @param partitionKey
 * @param members
 * @param zkPath
 * @param notification
 */
case class ZkPartition(partitionKey:ByteString,
                       members: Seq[Address],   //who have been assigned to be part of this partition
                       zkPath:String,           //where the partition data is stored
                       notification:Option[Any])//optional notification when the query was issued
/**
 * response for partition query
 * @param partitionKey
 */
case class ZkPartitionNotFound(partitionKey: ByteString)

/**
 * request for VM's enrolled partitions
 * @param address
 */
case class ZkListPartitions(address: Address)

/**
 * response for list partitions query
 * @param partitionKeys
 */
case class ZkPartitions(partitionKeys:Seq[ByteString])