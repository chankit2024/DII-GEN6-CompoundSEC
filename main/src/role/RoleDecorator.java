package role;

/**
 * RoleDecorator (Abstract Decorator)
 * มี wrappee ที่เป็น Role ด้านใน
 */
public abstract class RoleDecorator implements Role {
    protected Role wrappee;

    public RoleDecorator(Role wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public boolean canAccess(String area) {
        return wrappee.canAccess(area);
    }

    @Override
    public String getRoleName() {
        return wrappee.getRoleName();
    }
}
