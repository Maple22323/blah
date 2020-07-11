/*
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
 * Copyright (c) 2020, Ugnius <https://github.com/UgiR>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.http.api.config;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import net.runelite.http.api.RuneLiteApiClient;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConfigClient extends RuneLiteApiClient
{
	private static final String ENDPOINT = "config";
	private static final MediaType TEXT_PLAIN = MediaType.parse("text/plain");

	public ConfigClient(OkHttpClient client, UUID uuid)
	{
		super(client.newBuilder().addInterceptor(new ConfigClientInterceptor(uuid)), ENDPOINT);
	}

	public Configuration get() throws IOException
	{
		return bodyToObject(get_(), Configuration.class);
	}

	public CompletableFuture<Void> set(String key, String value)
	{
		return putAsync_(RequestBody.create(TEXT_PLAIN, value), url -> url.newBuilder().addPathSegment(key).build())
			.thenCompose(response -> bodyToObjectFuture(response, Void.class));
	}

	public CompletableFuture<Void> unset(String key)
	{
		return deleteAsync_(url -> url.newBuilder().addPathSegment(key).build())
			.thenCompose(response -> bodyToObjectFuture(response, Void.class));
	}

	@RequiredArgsConstructor
	private static class ConfigClientInterceptor implements Interceptor
	{

		private final UUID uuid;

		@Override
		public Response intercept(Chain chain) throws IOException
		{
			Request request = chain.request()
				.newBuilder()
				.header(AUTH_HEADER, uuid.toString())
				.build();

			return chain.proceed(request);
		}
	}
}