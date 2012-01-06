package org.herrlado.android.geotv;

import android.content.Context;

public class LogoFactory {

	private static RLogo createRLogo(Context ctx, String source) {
		int resId = ctx.getResources().getIdentifier(source, "drawable",
				ctx.getPackageName());
		if (resId == 0) {
			resId = R.drawable.ic_launcher;
		}
		return new RLogo(resId);
	}

	public static Logo create(Context ctx, String source) {

		if (source.startsWith("R.")) {
			return createRLogo(ctx, source.substring(2));
		}

		return createRLogo(ctx, "-");

	}

}
