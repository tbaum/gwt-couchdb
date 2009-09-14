package de.atns.abowind.proto1;

import com.allen_sauer.gwt.dnd.client.AbstractDragController;
import com.allen_sauer.gwt.dnd.client.util.DOMUtil;
import com.allen_sauer.gwt.dnd.client.util.Location;
import com.allen_sauer.gwt.dnd.client.util.WidgetLocation;
import com.google.gwt.user.client.ui.Widget;

/**
* @author tbaum
* @since 12.09.2009 19:20:10
*/
class TestDragController extends AbstractDragController {
    private TemplateDesignerPanel templateDesignerPanel;

    public TestDragController(TemplateDesignerPanel templateDesignerPanel) {
        super(templateDesignerPanel);
        this.templateDesignerPanel = templateDesignerPanel;
    }

    public void dragMove() {
        if (1 > 0) return;

        // may have changed due to scrollIntoView() or developer driven changes
        calcBoundaryOffset();

        int desiredLeft = context.desiredDraggableX - boundaryOffsetX;
        int desiredTop = context.desiredDraggableY - boundaryOffsetY;
        if (getBehaviorConstrainedToBoundaryPanel()) {
            desiredLeft = Math.max(0, Math.min(desiredLeft, dropTargetClientWidth
                    - context.draggable.getOffsetWidth()));
            desiredTop = Math.max(0, Math.min(desiredTop, dropTargetClientHeight
                    - context.draggable.getOffsetHeight()));
        }

        DOMUtil.fastSetElementPosition(movablePanel.getElement(), desiredLeft, desiredTop);
        /*
DropController newDropController = getIntersectDropController(context.mouseX, context.mouseY);
if (context.dropController != newDropController) {
if (context.dropController != null) {
  context.dropController.onLeave(context);
}
context.dropController = newDropController;
if (context.dropController != null) {
  context.dropController.onEnter(context);
}
}
        */
        if (context.dropController != null) {
            context.dropController.onMove(context);
        }
    }

    private void calcBoundaryOffset() {
        Location widgetLocation = new WidgetLocation(context.boundaryPanel, null);
        boundaryOffsetX = widgetLocation.getLeft()
                + DOMUtil.getBorderLeft(context.boundaryPanel.getElement());
        boundaryOffsetY = widgetLocation.getTop()
                + DOMUtil.getBorderTop(context.boundaryPanel.getElement());
    }

    private int boundaryOffsetX;

    private int boundaryOffsetY;
    private int dropTargetClientHeight;

    private int dropTargetClientWidth;
    private Widget movablePanel;

    @Override
    public void dragEnd() {
        if (1 > 0) return;


        movablePanel.removeFromParent();
        movablePanel = null;
        super.dragEnd();
    }
}
