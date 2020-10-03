package com.aeroshi.repositories.data.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "repositoriesTb")
data class Rep(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Long,
    @SerializedName("archive_url")
    val archiveUrl: String,
    @SerializedName("assignees_url")
    val assigneesUrl: String,
    @SerializedName("blobs_url")
    val blobsUrl: String,
    @SerializedName("branches_url")
    val branchesUrl: String,
    @SerializedName("collaborators_url")
    val collaboratorsUrl: String,
    @SerializedName("comments_url")
    val commentsUrl: String,
    @SerializedName("commits_url")
    val commitsUrl: String,
    @SerializedName("compare_url")
    val compareUrl: String,
    @SerializedName("contents_url")
    val contentsUrl: String,
    @SerializedName("contributors_url")
    val contributorsUrl: String,
    @SerializedName("deployments_url")
    val deploymentsUrl: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("downloads_url")
    val downloadsUrl: String,
    @SerializedName("events_url")
    val eventsUrl: String,
    @SerializedName("fork")
    val fork: Boolean,
    @SerializedName("forks_url")
    val forksUrl: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("git_commits_url")
    val gitCommitsUrl: String,
    @SerializedName("git_refs_url")
    val gitRefsUrl: String,
    @SerializedName("git_tags_url")
    val gitTagsUrl: String,
    @SerializedName("hooks_url")
    val hooksUrl: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("issue_comment_url")
    val issueCommentUrl: String,
    @SerializedName("issue_events_url")
    val issueEventsUrl: String,
    @SerializedName("issues_url")
    val issuesUrl: String,
    @SerializedName("keys_url")
    val keysUrl: String,
    @SerializedName("labels_url")
    val labelsUrl: String,
    @SerializedName("languages_url")
    val languagesUrl: String,
    @SerializedName("merges_url")
    val mergesUrl: String,
    @SerializedName("milestones_url")
    val milestonesUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("notifications_url")
    val notificationsUrl: String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("private")
    val `private`: Boolean,
    @SerializedName("pulls_url")
    val pullsUrl: String,
    @SerializedName("releases_url")
    val releasesUrl: String,
    @SerializedName("stargazers_url")
    val stargazersUrl: String,
    @SerializedName("statuses_url")
    val statusesUrl: String,
    @SerializedName("subscribers_url")
    val subscribersUrl: String,
    @SerializedName("subscription_url")
    val subscriptionUrl: String,
    @SerializedName("tags_url")
    val tagsUrl: String,
    @SerializedName("teams_url")
    val teamsUrl: String,
    @SerializedName("trees_url")
    val treesUrl: String,
    @SerializedName("url")
    val url: String

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rep

        if (id != other.id) return false
        if (archiveUrl != other.archiveUrl) return false
        if (assigneesUrl != other.assigneesUrl) return false
        if (blobsUrl != other.blobsUrl) return false
        if (branchesUrl != other.branchesUrl) return false
        if (collaboratorsUrl != other.collaboratorsUrl) return false
        if (commentsUrl != other.commentsUrl) return false
        if (commitsUrl != other.commitsUrl) return false
        if (compareUrl != other.compareUrl) return false
        if (contentsUrl != other.contentsUrl) return false
        if (contributorsUrl != other.contributorsUrl) return false
        if (deploymentsUrl != other.deploymentsUrl) return false
        if (description != other.description) return false
        if (downloadsUrl != other.downloadsUrl) return false
        if (eventsUrl != other.eventsUrl) return false
        if (fork != other.fork) return false
        if (forksUrl != other.forksUrl) return false
        if (fullName != other.fullName) return false
        if (gitCommitsUrl != other.gitCommitsUrl) return false
        if (gitRefsUrl != other.gitRefsUrl) return false
        if (gitTagsUrl != other.gitTagsUrl) return false
        if (hooksUrl != other.hooksUrl) return false
        if (htmlUrl != other.htmlUrl) return false
        if (issueCommentUrl != other.issueCommentUrl) return false
        if (issueEventsUrl != other.issueEventsUrl) return false
        if (issuesUrl != other.issuesUrl) return false
        if (keysUrl != other.keysUrl) return false
        if (labelsUrl != other.labelsUrl) return false
        if (languagesUrl != other.languagesUrl) return false
        if (mergesUrl != other.mergesUrl) return false
        if (milestonesUrl != other.milestonesUrl) return false
        if (name != other.name) return false
        if (nodeId != other.nodeId) return false
        if (notificationsUrl != other.notificationsUrl) return false
        if (owner != other.owner) return false
        if (`private` != other.`private`) return false
        if (pullsUrl != other.pullsUrl) return false
        if (releasesUrl != other.releasesUrl) return false
        if (stargazersUrl != other.stargazersUrl) return false
        if (statusesUrl != other.statusesUrl) return false
        if (subscribersUrl != other.subscribersUrl) return false
        if (subscriptionUrl != other.subscriptionUrl) return false
        if (tagsUrl != other.tagsUrl) return false
        if (teamsUrl != other.teamsUrl) return false
        if (treesUrl != other.treesUrl) return false
        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + archiveUrl.hashCode()
        result = 31 * result + assigneesUrl.hashCode()
        result = 31 * result + blobsUrl.hashCode()
        result = 31 * result + branchesUrl.hashCode()
        result = 31 * result + collaboratorsUrl.hashCode()
        result = 31 * result + commentsUrl.hashCode()
        result = 31 * result + commitsUrl.hashCode()
        result = 31 * result + compareUrl.hashCode()
        result = 31 * result + contentsUrl.hashCode()
        result = 31 * result + contributorsUrl.hashCode()
        result = 31 * result + deploymentsUrl.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + downloadsUrl.hashCode()
        result = 31 * result + eventsUrl.hashCode()
        result = 31 * result + fork.hashCode()
        result = 31 * result + forksUrl.hashCode()
        result = 31 * result + fullName.hashCode()
        result = 31 * result + gitCommitsUrl.hashCode()
        result = 31 * result + gitRefsUrl.hashCode()
        result = 31 * result + gitTagsUrl.hashCode()
        result = 31 * result + hooksUrl.hashCode()
        result = 31 * result + htmlUrl.hashCode()
        result = 31 * result + issueCommentUrl.hashCode()
        result = 31 * result + issueEventsUrl.hashCode()
        result = 31 * result + issuesUrl.hashCode()
        result = 31 * result + keysUrl.hashCode()
        result = 31 * result + labelsUrl.hashCode()
        result = 31 * result + languagesUrl.hashCode()
        result = 31 * result + mergesUrl.hashCode()
        result = 31 * result + milestonesUrl.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + nodeId.hashCode()
        result = 31 * result + notificationsUrl.hashCode()
        result = 31 * result + owner.hashCode()
        result = 31 * result + `private`.hashCode()
        result = 31 * result + pullsUrl.hashCode()
        result = 31 * result + releasesUrl.hashCode()
        result = 31 * result + stargazersUrl.hashCode()
        result = 31 * result + statusesUrl.hashCode()
        result = 31 * result + subscribersUrl.hashCode()
        result = 31 * result + subscriptionUrl.hashCode()
        result = 31 * result + tagsUrl.hashCode()
        result = 31 * result + teamsUrl.hashCode()
        result = 31 * result + treesUrl.hashCode()
        result = 31 * result + url.hashCode()
        return result
    }
}