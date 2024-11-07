package au.edu.rmit.sept.webapp.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "saved_resources")
public class SavedResources {

    @Embeddable
    public static class SavedResourcesID implements Serializable {
        private Long user;
        private Long resource;

        public SavedResourcesID() {}

        public SavedResourcesID(Long user, Long resource) {
            this.user = user;
            this.resource = resource;
        }

        // Getters and Setters
        public Long getUser() {
            return user;
        }

        public void setUser(Long user) {
            this.user = user;
        }

        public Long getResource() {
            return resource;
        }

        public void setResource(Long resource) {
            this.resource = resource;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SavedResourcesID)) return false;
            SavedResourcesID that = (SavedResourcesID) o;
            return Objects.equals(user, that.user) && Objects.equals(resource, that.resource);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user, resource);
        }
    }

    @EmbeddedId
    private SavedResourcesID id;

    @ManyToOne
    @MapsId("user")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private CustomUser user;

    @ManyToOne
    @MapsId("resource")
    @JoinColumn(name = "resource_id", referencedColumnName = "resource_id")
    private EduResources resources;

    @Column(name = "saved_at")
    private LocalDateTime savedAt;

    // Getters and Setters
    public SavedResourcesID getID() {
        return id;
    }

    public void setID(SavedResourcesID id) {
        this.id = id;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
        if (this.id == null) {
            this.id = new SavedResourcesID();
        }
        this.id.setUser(user.getUserId()); 
    }

    public EduResources getResources() {
        return resources;
    }

    public void setResources(EduResources resources) {
        this.resources = resources;
        if (this.id == null) {
            this.id = new SavedResourcesID();
        }
        this.id.setResource(resources.getResourceID());
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }
}
